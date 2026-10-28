import { createRouter, createWebHashHistory } from 'vue-router';
import { useAuth } from '../stores/auth';
import SignIn from '../components/SignIn.vue';
import Settings from '../components/Settings.vue';
import MyDashboard from '../components/MyDashboard.vue';

// Dynamic imports for module components
import Orders from '../components/Orders.vue';
import OrderDetail from '../components/OrderDetail.vue';
import OrderItems from '../components/OrderItems.vue';
import OrderItemDetail from '../components/OrderItemDetail.vue';
import OrderModifiers from '../components/OrderModifiers.vue';
import OrderModifierDetail from '../components/OrderModifierDetail.vue';
import OrderStatuss from '../components/OrderStatuss.vue';
import OrderStatusDetail from '../components/OrderStatusDetail.vue';

const routes = [
  {
    path: '/',
    name: 'home',
    redirect: '/dashboard'
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: MyDashboard
  },
  
  // Dynamic module routes
  {
    path: '/orders',
    name: 'Orders',
    component: Orders
  },
  {
    path: '/order/:id',
    name: 'OrderDetail',
    component: OrderDetail,
    props: true
  },
  {
    path: '/orderItems',
    name: 'OrderItems',
    component: OrderItems
  },
  {
    path: '/orderItem/:id',
    name: 'OrderItemDetail',
    component: OrderItemDetail,
    props: true
  },
  {
    path: '/orderModifiers',
    name: 'OrderModifiers',
    component: OrderModifiers
  },
  {
    path: '/orderModifier/:id',
    name: 'OrderModifierDetail',
    component: OrderModifierDetail,
    props: true
  },
  {
    path: '/orderStatuss',
    name: 'OrderStatuss',
    component: OrderStatuss
  },
  {
    path: '/orderStatus/:id',
    name: 'OrderStatusDetail',
    component: OrderStatusDetail,
    props: true
  },

  // Core application routes
  {
    path: '/signin',
    name: 'SignIn',
    component: SignIn
  },
//  {
//    path: '/users',
//    name: 'Users',
//    component: Users,
//    meta: { requiresAuth: true }
//  },
  {
    path: '/settings',
    name: 'Settings',
    component: Settings,
    meta: { requiresAuth: true }
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/dashboard'
  }
];

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes,
  scrollBehavior(to, from, savedPosition) {
    return savedPosition || { top: 0 };
  }
});

// Navigation guard for authentication
router.beforeEach((to, from, next) => {
  const auth = useAuth();
  
  if (to.meta.requiresAuth && !auth.isAuthenticated) {
    next({ name: 'SignIn', query: { redirect: to.fullPath } });
  } else if (to.name === 'SignIn' && auth.isAuthenticated) {
    next({ name: 'Dashboard' });
  } else {
    next();
  }
});

export default router;
