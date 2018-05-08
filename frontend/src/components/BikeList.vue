<template>
	<div class="bike-list">
		<ul>
			<li v-for="bike in bikes">
				{{ bike.name }}
				<ul>
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
	}
}
</script>
