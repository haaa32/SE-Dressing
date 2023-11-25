import '../css/LoginPage.css';
import logo from '../images/logo.png';

function App1() {
  const GoApp2=()=>{
    window.location.href='/main'
  }

  return (
    <div id="App">
          <div id="header">
            <div id="logo">
              <img src={logo}/>
            </div>
            <div id="head"></div>
          </div>
          <div id="log">
            <div id="login">
              <div id="form">
                <input type="text" placeholder="ID"/>
                <br/>
                <input type="password" placeholder="PW"/>
                <br/>
                <input type="submit" value="Login" id="login_btn" onClick={GoApp2}/>
                <br/>
                <div id="member">
                  <a href='/membership'>회원가입</a>
                  <br/>
                  <a href='/admin'>관리자</a>
                </div>
              </div>
            </div>
            </div>
        </div>
  );
}

export default App1;