<template>
	<div>{{ developmts }}</div>
</template>

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
				});	
			}, response => {
				//TODO error	
			});
	}
}
</script>
