<template>
	<div id='particles-js'></div>
</template>

<style scope>
	#particles-js{
		width: 100%;
		height: 100%;
		background-color: #000000;
		background-image: url('');
		background-size: cover;
		background-position: 50% 50%;
		background-repeat: no-repeat;
	}	
</style>

<script>
export default {
	name: 'ParticlesJS',
	mounted () {
		require('particles.js');
		this.$nextTick(() => {
			this.initParticlesJS();
		})
	},
	methods: {
		initParticlesJS () {
			var particles = require('../config/particles');
			particlesJS('particles-js',particles);
		}
	}
}
</script>
