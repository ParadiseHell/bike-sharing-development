<template>
	<router-view/>
</template>

<style>
html,body{ 
	width:100%;
	height:100%;
}
canvas{
  display:block;
  vertical-align:bottom;
}
</style>

<script>
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import 'pace-js/themes/blue/pace-theme-flash.css'

export default {
	name: 'App'
}
</script>
