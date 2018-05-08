<template>
	<div>{{ msg }}</div>
</template>

<script>
export default {
  name: 'HelloWorld',
  data () {
    return {
      msg: 'Bike Sharing Development'
    }
  },
	mounted(){
		this.msg = 'I changed';
	}
}
</script>
