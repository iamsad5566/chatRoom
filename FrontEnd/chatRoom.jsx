import React, { useState } from 'react';
import NavBar from '../../nav/nav';
import ChatRoomUI from './chatRoomUI';
import DocumentMeta from 'react-document-meta';

const ChatRoom = () => {
    const meta = {
        title:"Chat room",
        description:"chat room",
        meta:{
            charset:"utf-8",
            property:{
                "og:url":"https://tw-yk.website/testing/chatRoom",
                "og:locale":"zh_TW",
                "og:type":"chat room",
                "og:description":"Welcome to my website! I'm dedicated to updating this web, so just feel free to come anytime, maybe you will find something new and interesting!",
                "og:title":"純愛聊天室",
                "og:image:type":"image/png",
                "og:image":"pi512.png",
                "og:image:width":"1400",
                "og:image:height":"770"
            }
        }
    }

    const [nickname, setNickname] = useState("");
    const [done, setDone] = useState(false);

    const styleForChatRoom = {
        height:"100vh",
        margin:"auto",
        display:"flex", 
        alignItems:"center", 
        justifyContent:"center"
    }

    function handleChange(event) {
        setNickname( event.target.value );
    }

    function handleClick(event) {
        event.preventDefault();
        if( nickname.length > 0 )
           setDone(true);
    }
    
    return ( 
        <div>
            <DocumentMeta {...meta} />
            <NavBar/>
            <div>
                {done?
                    <ChatRoomUI nickname={nickname}/>
                    :(<div style={styleForChatRoom}>
                        <div>
                            <h3 style={{textAlign:"center", margin:"1em"}}>請輸入暱稱：</h3>

                            <form className="input-group" style={{width:"20em"}}>
                                <input type="text" name="nickname" value={nickname} className="form-control" placeholder="Type your nickname" onChange={event => handleChange(event)} autoComplete="off"/>
                                <button className="btn btn-primary" type="submit" onClick={event => handleClick(event)}>Send</button>
                            </form>

                        </div>
                    </div>)
                }
            </div>
        </div>
     );
}
 
export default ChatRoom;