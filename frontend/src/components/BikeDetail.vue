<template>
	<div id="bike-detail"></div>
</template>

<style scoped>
#bike-detail{
	height: 100%;
	width: 100%;
}
</style>

<script>
import API from '../API';
export default {
	name: 'BikeDetail',
	data () {
		return {
			developmts : []
		}
	},
	mounted(){
		this
			.$http
			.get(
				API.developments,
				{
					params: {
						bike_id: this.$route.params.bikeId
					}
				}	
			)
			.then( response => {
				response.json().then(data => {
					this.developmts = data;
					//
					var echarts = require('echarts');
					require('echarts/extension/bmap/bmap');
					var myChart = echarts.init(document.getElementById('bike-detail'));
					var data = [
						{name: '海门', value: 100},
						{name: '鄂尔多斯', value: 80},
						{name: '招远', value: 50},
						{name: '舟山', value: 80}	
					]
					var geoCoordMap = {
						'海门':[121.15,31.89],
						'鄂尔多斯':[109.781327,39.608266],
						'招远':[120.38,37.35],
						'舟山':[122.207216,29.985295]
					}
					var option = {
						title: {
							text: '全国主要城市空气质量 - 百度地图',
							subtext: 'data from PM25.in',
							sublink: 'http://www.pm25.in',
							left: 'center'
						},
						tooltip : {
							trigger: 'item'
						},
						bmap: {
							center: [104.114129, 37.550339],
							zoom: 5,
							roam: true,
							mapStyle: {
								styleJson: [{
									'featureType': 'water',
									'elementType': 'all',
									'stylers': {
										'color': '#d1d1d1'
									}
								}, {
									'featureType': 'land',
									'elementType': 'all',
									'stylers': {
										'color': '#f3f3f3'
									}
								}, {
									'featureType': 'railway',
									'elementType': 'all',
									'stylers': {
										'visibility': 'off'
									}
								}, {
									'featureType': 'highway',
									'elementType': 'all',
									'stylers': {
										'color': '#fdfdfd'
									}
								}, {
									'featureType': 'highway',
									'elementType': 'labels',
									'stylers': {
										'visibility': 'off'
									}
								}, {
									'featureType': 'arterial',
									'elementType': 'geometry',
									'stylers': {
										'color': '#fefefe'
									}
								}, {
									'featureType': 'arterial',
									'elementType': 'geometry.fill',
									'stylers': {
										'color': '#fefefe'
									}
								}, {
									'featureType': 'poi',
									'elementType': 'all',
									'stylers': {
										'visibility': 'off'
									}
								}, {
									'featureType': 'green',
									'elementType': 'all',
									'stylers': {
										'visibility': 'off'
									}
								}, {
									'featureType': 'subway',
									'elementType': 'all',
									'stylers': {
										'visibility': 'off'
									}
								}, {
									'featureType': 'manmade',
									'elementType': 'all',
									'stylers': {
										'color': '#d1d1d1'
									}
								}, {
									'featureType': 'local',
									'elementType': 'all',
									'stylers': {
										'color': '#d1d1d1'
									}
								}, {
									'featureType': 'arterial',
									'elementType': 'labels',
									'stylers': {
										'visibility': 'off'
									}
								}, {
									'featureType': 'boundary',
									'elementType': 'all',
									'stylers': {
										'color': '#fefefe'
									}
								}, {
									'featureType': 'building',
									'elementType': 'all',
									'stylers': {
										'color': '#d1d1d1'
									}
								}, {
									'featureType': 'label',
									'elementType': 'labels.text.fill',
									'stylers': {
										'color': '#999999'
									}
								}]
							}
						},
						series : [
							{
								name: 'pm2.5',
								type: 'scatter',
								coordinateSystem: 'bmap',
								data:  this.convertData(data, geoCoordMap),
								symbolSize: function (val) {
									return val[2] / 10;
								},
								label: {
									normal: {
										formatter: '{b}',
										position: 'right',
										show: false
									},
									emphasis: {
										show: true
									}
								},
								itemStyle: {
									normal: {
										color: 'purple'
									}
								}
							},
							{
								name: 'Top 5',
								type: 'effectScatter',
								coordinateSystem: 'bmap',
								data: this.convertData(data.sort(function (a, b) {
									return b.value - a.value;
								}).slice(0, 6), geoCoordMap),
								symbolSize: function (val) {
									return val[2] / 10;
								},
								showEffectOn: 'render',
								rippleEffect: {
									brushType: 'stroke'
								},
								hoverAnimation: true,
								label: {
									normal: {
										formatter: '{b}',
										position: 'right',
										show: true
									}
								},
								itemStyle: {
									normal: {
										color: 'purple',
										shadowBlur: 10,
										shadowColor: '#333'
									}
								},
								zlevel: 1
							}
						]
					}
					myChart.setOption(option, true);
				});	
			}, response => {
				//TODO error	
			});
	},
	methods: {
		convertData: function (data, geoCoordMap) {
			var res = [];
			for (var i = 0; i < data.length; i++) {
				var geoCoord = geoCoordMap[data[i].name];
				if (geoCoord) {
					res.push({
						name: data[i].name,
						value: geoCoord.concat(data[i].value)
					});
				}
			}
			return res;
		}
	}
}
</script>
