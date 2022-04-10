import React from "react";

const NavItem = ({ href, children }) => {
  return (
    <li className="nav-item">
      <a className="nav-link js-scroll-trigger" href={href}>
        {children}
      </a>
    </li>
  );
};

export default NavItem;
