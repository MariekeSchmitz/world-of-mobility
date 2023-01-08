/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.html", "./src/**/*.vue"],
  theme: {
    colors: {
      'green-dark': '#2F8265',
      'green-light': '#2F826520',
      'orange': '#FFB72E',
      'orange-light': '#FFB72E20',
    },
    fontFamily: {
      sans : ['Poppins', 'sans-serif'],
      'fredoka': ['"Fredoka One"', 'regular']
    },
    extend: {
      gridTemplateColumns: {
        '16': 'repeat(16, minmax(0, 1fr))'
      }
    },
  },
  plugins: [],
}
