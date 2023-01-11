/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.html", "./src/**/*.vue"],
  theme: {
    colors: {
      greenDark: '#2F8265',
      greenLight: '#2F826520',
      orange: '#FFB72E',
      orangeLight: '#FFB72E20',
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
