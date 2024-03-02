import {createRouter, createWebHistory} from "vue-router";

const Login =() =>import("../views/Login.vue")
const Main =() =>import("../views/Main.vue")
const SignUp = () => import("../views/SignUp.vue")

const routes = [
    {path: "/login", component: Login},
    {
        path: "/main",
        component: Main,
        beforeEnter: (to, from, next) => {
            fetch('http://localhost:8080/api-signIn', {
                method: "POST",
                credentials: "include",
                headers: {
                    'Content-Type': 'application/json',
                    credentials: 'include'
                }
            }).then(res => {
                return res.status === 200 ?  next() : next('/login');
            })
        }
    },
    {path: "/signUp", component: SignUp},
    {path: "/", redirect: {path: "/login"}}
]

const router = createRouter({
    history:createWebHistory(),
    routes
})

export default router;