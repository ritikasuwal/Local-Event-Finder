import React from 'react'

const Footer = () => {
  return (
    <div className='footer_container'>
        <div className="footer1">
            <h3>Local Event Finder</h3>
        </div>
        <div className="footer2">
            <p>© 2024 Local Event Finder. All rights reserved.</p>
        </div>
        <div className="footer3">  
            <a href="/about">About Us</a>
            <a href="/contact">Contact</a>
            <a href="/privacy">Privacy Policy</a>
            <a href="/terms">Terms of Service</a>
        </div>
        <div className="footer4">
            <p>Follow us:</p>
            <a href="https://facebook.com" target="_blank" rel="noopener noreferrer">Facebook</a>
            <a href="https://twitter.com" target="_blank" rel="noopener noreferrer">Twitter</a>
            <a href="https://instagram.com" target="_blank" rel="noopener noreferrer">Instagram</a>
        </div>
      
    </div>
  )
}

export default Footer
