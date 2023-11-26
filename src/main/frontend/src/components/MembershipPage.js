import '../css/MembershipPage.css';
import logo from '../images/logo.png';

function App4() {

    const GoApp1=()=>{
        window.location.href='/'
      }

    return (
        <div id="App">
            <div id="header4">
                <div id="logo">
                    <img src={logo}/>
                </div>
                <div id="head">여백</div>
            </div>
            <div id="membership">
                회원가입창
                <div id="JoinMembership">
                    <div id="form">
                        <input type="name" placeholder="Name"/><br/>
                        <input type="text" placeholder="ID"/><br/>
                        <input type="password" placeholder="PW"/><br/>
                        <input type="password" placeholder="PWCheck"/><br/>
                        <input type="phonenumber" placeholder="PhoneNumber"/><br/>
                        <input type="submit" value="create account" id="create_btn" onClick={GoApp1}/>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default App4;