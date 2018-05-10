<template>
	<div id="bike-list">
		<particlesJS/>
		<b-container class="bike-list">
			<b-row>
				<b-col cols="6" offset="3" v-for="bike in obtainList" :key="bike.id">
					<b-card
						v-bind:title="bike.name"
						class="mb-4"
						v-bind:style="{
							'border-color': bike.color, 
							color: bike.color
						}">
						<p class="card-text">	
						成立时间: {{ bike.founded_at }}
						</p>
						<p class="card-text">	
						{{ bike.introduction }}
						</p>
						<div class="text-center">
							<b-button 
								 v-bind:style="{
									'border-color': bike.color
									}"
								 v-on:mouseover="changeButtonColor(bike.color, $event)"
								 v-on:mouseleave="resetButtonColor"
								>
								查看发展变化
							</b-button>
						</div>
					</b-card>
				</b-col>
			</b-row>
		</b-container>
	</div>
</template>

<style scoped>
#bike-list{
	width: 100%;
	height: 100%;
}
.bike-list{
	position: absolute;
	top: 30px;
	left: 50%;
	transform: translateX(-50%);
}
.card{
	background-color: transparent;
}
button{
	background-color: transparent;
}
</style>

<script>
import API from '../API';
import particlesJS from './ParticlesJS';

export default {
	name: 'BikeList',
	components : {
		particlesJS
	},
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
				let name = bike.name;
				if(name.indexOf("ofo") != -1){
					bike.color = "#ffd900";
				}else if(name.indexOf("摩拜") != -1){
					bike.color = "#e23d0e";
				}
				return bike;
			});
		}
	},
	methods: {
		changeButtonColor: function(color, event){
			event.target.style.backgroundColor = color;
		},	
		resetButtonColor: function(event){
			event.target.style.backgroundColor = "transparent";
		}	
	}
}
</script>
