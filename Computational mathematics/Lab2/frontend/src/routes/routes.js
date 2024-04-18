import {createRouter, createWebHistory} from "vue-router";

import Lab2Part1 from '../views/Lab2Part1.vue'
import Lab2Part2 from '../views/Lab2Part2.vue'
import Lab3 from '../views/Lab3.vue'
import Lab4 from '../views/Lab4.vue'
import Lab5 from '../views/Lab5.vue'
import Lab6 from '../views/Lab6.vue'

const routes = [
    {path: "/Lab2Part1", component: Lab2Part1},
    {path: "/Lab2Part2", component: Lab2Part2},
    {path: "/Lab3", component: Lab3},
    {path: "/Lab4", component: Lab4},
    {path: "/Lab5", component: Lab5},
    {path: "/Lab6", component: Lab6},
    {path: "/", redirect: {path: "/Lab2Part1"}}
]

const router = createRouter({
    history:createWebHistory(),
    routes
})

export default router;