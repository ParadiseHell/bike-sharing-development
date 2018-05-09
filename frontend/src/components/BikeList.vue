<template>
	<div class="bike-list">
		<ul>
			<li v-for="bike in obtainList">
				{{ bike.name }}
				<ul>
					<li>简介: {{ bike.introduction }}</li>
					<li>成立时间: {{ bike.founded_at }}</li>
				</ul>
			</li>
		</ul>
	</div>
</template>

<script>
import API from '../API';

export default {
  name: 'BikeList',
  data () {
    return {
			bikes:[]
    }
  },
	mounted(){
		this.$http.get(API.bikes).then( response => {
			response.json().then(data => {
				this.bikes = data;
			});	
		}, response => {
			//TODO error	
		});
	},
	computed: {
		obtainList: function(){
			return this.bikes.map(function(bike){
				let foundedAtUTCDate = new Date(bike.founded_at);
				bike.founded_at = 
					foundedAtUTCDate.getFullYear() + " 年 "+
					(foundedAtUTCDate.getMonth() + 1) + " 月 "+
					foundedAtUTCDate.getDate() + " 日";
				return bike;
			});
		}
	}
}
</script>
