<template>
	<div id="bike-list">
		<particlesJS/>
		<b-container class="bike-list">
			<b-row>
				<b-col 
								 lg="4"
								 offset-lg="4"
								 md="8"
								 offset-md="2"
								 sm="10"
								 offset-sm="1"
								 v-for="bike in obtainList" :key="bike.id">
					<b-card
						v-bind:title="bike.name"
						class="mb-4"
						v-bind:style="{
						'border-color': bike.color, 
						color: bike.color
						}">
						<p class="card-text">	
						{{ bike.display_founded_at }}
						</p>
						<p class="card-text">	
						{{ bike.introduction }}
						</p>
						<div class="text-center">
							<router-link
								v-bind:to="{name: 'BikeDetail', params: { bikeId: bike.id }}">
								<b-button 
								class="m-btn"
								v-bind:style="{
								'border-color': bike.color,
								'color': bike.color,
								}"
								v-on:mouseover="changeButtonColor(bike.color, $event)"
								v-on:mouseleave="resetButtonColor(bike.color, $event)"
								variant="outline-light"
								>
								{{ detailDevlopment }}	
								</b-button>
							</router-link>
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
	max-height: 100%;
	position: absolute;
	top: 0px;
	padding-top: 50px;
	padding-bottom: 50px;
	left: 50%;
	transform: translateX(-50%);
	overflow: auto;
}
.card{
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
			bikes: [],
			detailDevlopment: "查看发展变化"
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
				bike.display_founded_at = 
					"成立时间 : " + 
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
			event.target.style.color = "#fff";
		},	
		resetButtonColor: function(color, event){
			event.target.style.backgroundColor = "transparent";
			event.target.style.color = color;
		}	
	}
}
</script>
