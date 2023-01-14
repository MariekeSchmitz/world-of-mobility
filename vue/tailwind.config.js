/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.html", "./src/**/*.vue"],
  theme: {
    colors: {
      greenOverlay: "#2e362e",
      greenDark: "#2F8265",
      greenLight: "#CDDDD7",
      orange: "#FFB72E",
      orangeLight: "#F6E8CD",
      white: "#ffffff",
      black: "#000000",
      transparent: "transparent",
    },
    fontFamily: {
      poppins: ["Poppins", "sans-serif"],
      fredoka: ['"Fredoka One"', "regular"],
    },
    extend: {
      gridTemplateColumns: {
        16: "repeat(16, minmax(0, 1fr))",
      },
    },
  },
  plugins: [],
};
