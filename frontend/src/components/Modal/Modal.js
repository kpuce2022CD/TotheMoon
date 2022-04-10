import React from "react";
import "./Modal.css";

const Modal = ({ setVisible, visible, popUp }) => {
  return visible ? (
    <div id="modal" className="modal-overlay">
      <div className="modal-window">
        <div className="title">
          <h2>{popUp.title}</h2>
        </div>
        <div
          className="close-area"
          onClick={() => {
            setVisible(false);
          }}
        >
          X
        </div>
        <div className="content">
          {popUp.comments.map((cur, index) => (
            <>
              <p key={index} dangerouslySetInnerHTML={{ __html: cur }} />
              <hr />
            </>
          ))}
        </div>
      </div>
    </div>
  ) : null;
};

export default Modal;
