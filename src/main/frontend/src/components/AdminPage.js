import '../css/AdminPage.css';
import logo from '../images/logo.png';

function App5() {
    const changemodify=()=>{
        document.getElementById('admin_proposal').style.display = 'none';
        document.getElementById('admin_modify').style.display = 'block';
    }
    
    const changeproposal=()=>{
        document.getElementById('admin_modify').style.display = 'none';
        document.getElementById('admin_proposal').style.display = 'block';
    }

  return (
    <div id="App">
        <div id="header5">
            <div id="logo">
              <img src={logo}/>
            </div>
            <div id="head">여백</div>
        </div>
        <div id="category5">
            카테고리
            <nav class="nav">
                <ul class="gnb">
                    <li><a href="#" onClick={changeproposal}>건의사항</a></li>
                    <li><a href="#" onClick={changemodify}>회원정보수정</a></li>
                </ul>
            </nav>
        </div>
        <div id="admin">
            <div id="admin_proposal">건의사항확인</div>
            <div id="admin_modify">회원정보수정확인</div>
        </div>
    </div>
  );
}

export default App5;