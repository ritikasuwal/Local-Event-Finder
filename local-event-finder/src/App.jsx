import { useState } from 'react'

import './App.css'
import Navbar from './assets/components/Navbar.jsx'
import Footer from './assets/components/Footer.jsx'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <Navbar/>
      <Footer/>

    </>
  )
}

export default App
