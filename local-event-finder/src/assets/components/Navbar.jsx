import React from "react";
import "../css/Navbar.css";

const Navbar = () => {
  return (
    <div className="navbar" data-model-id="59:1016">
      {/* Left: Logo */}
      <div className="logo">MyLogo</div>

      {/* Middle: Search + Location */}
      <div className="searchbar">
        <div className="rectangle" />
        <div className="search-events">Search Events.....</div>
        <div className="ellipse" />

        <img className="search" alt="Search" src="https://c.animaapp.com/77VIlnRc/img/search@2x.png" />
        <img className="line" alt="Line" src="https://c.animaapp.com/77VIlnRc/img/line-1.svg" />

        <select className="kathmandu-NP">
          <option>Kathmandu, NP</option>
          <option>Bhaktapur, NP</option>
          <option>Lalitpur, NP</option>
        </select>

        <img className="multiply" alt="Multiply" src="https://c.animaapp.com/77VIlnRc/img/multiply@2x.png" />
      </div>

      {/* Right: Login / Signup */}
      <div className="btns">
        <button className="login-btn">Login</button>
        <button className="signup-btn">Sign Up</button>
      </div>
    </div>
  );
};

export default Navbar;
