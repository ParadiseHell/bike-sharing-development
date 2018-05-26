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
//引入相关插件
var echarts = require('echarts');
require('echarts/extension/bmap/bmap');

export default {
	name: 'BikeDetail',
	data () {
		return {
			beijingLocation : [116.403119,39.914714],

		}
	},
	mounted(){
		let apiUrl = API.bikes + "/" + this.$route.params.bikeId + "/developments";
		this
			.$http
			.get(apiUrl)
			.then( response => {
				response.json().then(developmts => {
					//
					var bikeDevelopmentChart = echarts.init(document.getElementById('bike-detail'));
					var data = [];
					var geoCoordMap = {};
					developmts.forEach(developmt =>{
						data.push({name: developmt.city, value: developmt.delivery_count});
						geoCoordMap[developmt.city] = [
							developmt.location.longitude,
							developmt.location.latitude
						];
					});
					var option = {
						title: {
							text: this.$route.query.bikeName + " - 发展变化",
							left: 'center'
						},
						tooltip : {
							trigger: 'item'
						},
						bmap: {
							center: this.beijingLocation,
							zoom: 6,
							roam: true,
						},
						series : [
							{
								name: this.$route.query.bikeName,
								type: 'effectScatter',
								coordinateSystem: 'bmap',
								data: this.convertData(data, geoCoordMap),
								symbolSize: function (val) {
									return val[2] / 4000;
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
										color: this.$route.query.color,
										shadowBlur: 10,
										shadowColor: '#333'
									}
								},
								zlevel: 1
							}
						]
					}
					//设置
					bikeDevelopmentChart.setOption(option, true);
				});	
			}, response => {
				//TODO error	
			});
	},
	methods: {
		convertData: function (data,geoCoordMap) {
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
