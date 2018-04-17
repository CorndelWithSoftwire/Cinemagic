import React from 'react';

/**
 * Error Modal which is displayed on top of the page when the application encounters an error which
 * can be recovered from, for example if the data inputted by the user is incorrect and the user
 * must correct it.
 */
export default function ErrorModal(props) {
    return (
      <div id="errorModal" className="errorModal">
        <div className="modal-content">
          <p className="errorMessage">{props.message}</p>
          <button onClick={() => props.onClose()}>Ok</button>
        </div>
      </div>
    );
}
