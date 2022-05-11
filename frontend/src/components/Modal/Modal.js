import React from "react";
import "./Modal.css";

const Modal = ({ setVisible, visible, popUp }) => {
  return visible ? (
    <div id="modal" className="modal-overlay">
      <div className="modal-window">
        <div className="title">
          <h2>{popUp.title}</h2>
          <div
          className="close-area"
          onClick={() => {
            setVisible(false);
          }}
        >
          X
        </div>
        </div>
        
        <div className="content">
          {popUp.comments.map((cur, index) => (
            <>
              <p style={{margin:"10px"}} key={index} dangerouslySetInnerHTML={{ __html: cur }} />    
              <hr></hr>        
            </>
          ))}
        </div>
      </div>
    </div>
  ) : null;
};

export default Modal;
