import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/components/Index'
import BikeList from '@/components/BikeList'
import BikeDetail from '@/components/BikeDetail'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Index',
      component: Index 
    },
    {
      path: '/bikes',
      name: 'BikeList',
      component: BikeList 
    },
    {
			path: '/bikes/:bikeId',
      name: 'BikeDetail',
      component: BikeDetail 
    }
  ]
})
