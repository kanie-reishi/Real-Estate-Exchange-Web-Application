/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['*.html'],
  theme: {
      colors: {
        'secondary': '#004dd1',
        'gray-v1': '#ececec',
        'gray-v2': '#f2f4f8',
        'gray-v3': '#f2f3f5',
        'black-v6': '#717171',
        'black-v8': '#343434',
        'green-v3' : '#00d623',
        'red-v6': '#e35b47',
      },
  },
  plugins: [],
  variants: {
    extend: {
      backgroundColor: ['hover'], // add 'hover' to the array if it's not already there
      flex: ['hover', 'focus'],
      darkMode: 'class',
    }
  },
  important: true,
}

