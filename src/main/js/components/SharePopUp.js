import React from 'react';

let shareLink = document.location.protocol + "//" + document.location.host + "/beat/"

const SharePopUp = (props) => (
    <div className="sharePopUp pop-up trs">
        <div className="pop-up-header">
            Поделиться
            <img src={'https://i.ibb.co/FnGGGTx/close.png'} alt="close"
                 width="18px" onClick={props.closePopUps}/>
        </div>
        <div className="share-link flex-c">
            <img src={'https://i.ibb.co/rsL0r6P/share.png'} width="14px" alt="share"/>

            <input value={shareLink + props.beat.id} readOnly/>
        </div>
    </div>
);

export default SharePopUp;