/** @type {import('tailwindcss').Config} */
export default {
  content: process.env.NODE_ENV == 'production' 
    ? ["./public/js/main.js"] 
    : ["./src/app/**/*.cljs", "./public/js/main.js"],
  theme: {
    extend: {},
  },
  plugins: [],
}

