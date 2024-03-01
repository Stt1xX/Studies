import {createRouter, createWebHistory} from "vue-router";

const Login =() =>import("../views/Login.vue")
const Main =() =>import("../views/Main.vue")
const SignUp = () => import("../views/SignUp.vue")

const routes = [
    {path: "/login", component: Login},
    {path: "/main", component: Main},
    {path: "/signUp", component: SignUp},
    {path: "/", redirect: {path: "/login"}}
]

const router = createRouter({
    history:createWebHistory(),
    routes
})

export default router;