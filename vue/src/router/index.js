import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "../store";

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        // name: 'Manage',
        component: () => import('../views/Manage.vue'),
        redirect: "/home",
        children: [
            {
                path: "home", name: "首頁", component: () => import("../views/Home.vue")
            },
            {
                path: "user", name: "會員管理", component: () => import("../views/User.vue")
            },
            {
                path: "person", name: "個人訊息", component: () => import("../views/Person.vue")
            },
            {
                path: "file", name: "文件管理", component: () => import("../views/File.vue")
            }
        ]
    },
    {
        path: '/about',
        name: 'About',
        component: () => import('../views/About.vue')
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/Login.vue')
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import('../views/Register.vue')
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

router.beforeEach((to, from, next) => {
    // console.log(from);
    localStorage.setItem("currentPathName", to.name);
    store.commit("setPath")
    next();
})

export default router
