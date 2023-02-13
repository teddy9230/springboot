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
                path: "home", name: "首頁", component: () => import("../components/Home.vue")
            },
            {
                path: "user", name: "會員管理", component: () => import("../components/User.vue")
            }
        ]
    },
    {
        path: '/about',
        name: 'About',
        component: () => import('../views/About.vue')
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
