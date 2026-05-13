// https://nuxt.com/docs/api/configuration/nuxt-config
const BACKEND_URL = process.env.BACKEND_URL ?? 'http://backend:5000'

export default defineNuxtConfig({
  compatibilityDate: '2025-07-15',
  devtools: { enabled: true },

  modules: [
    '@pinia/nuxt',
    '@nuxtjs/tailwindcss',
    '@nuxtjs/i18n',
    '@nuxtjs/eslint-module',
    '@nuxt/eslint',
    '@nuxt/fonts'
  ],

  tailwindcss: {
    cssPath: '~/assets/css/main.css',
  },

  routeRules: {
    '/api/**': { proxy: `${BACKEND_URL}/**` }
  }
})