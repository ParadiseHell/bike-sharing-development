<template>
	<div id="index">
		<particlesJS/>
		<b-container class="intro">
			<h3 class="text-white">
				<span 
					v-html="title" 
					class="align-middle">
				</span> 
			</h3>
			<div class="col-md-12 text-center"> 
				<b-btn 
					 class="mt-3"
					 variant="outline-light"
					 size="lg" 
					 to="bikes"
					 >
					 {{ detail }}
				</b-btn>
			</div>
		</b-container>
	</div>
</template>

<style scoped>
#index {
	width: 100%;
	height: 100%;
}
.intro {
	position: absolute;
	width: 360px;
	height: 180px;
	top: 50%;
	left: 50%;
	margin: -90px 0 0 -180px;
	padding : 18px;
}
span{
	display: block;
	text-align: center;
	line-height: 45px;
}
</style>

<script>
import particlesJS from './ParticlesJS'

export default {
	name: 'Index',
	components : {
		particlesJS
	},
	data () {
		return {
			title: '基于互联网大数据分析<br>共享单车区域发展的变化',
			detail: '查看详情'
		}
	}
}
</script>
