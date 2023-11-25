import '../css/MainPage.css';
import logo from '../images/logo.png';

function App2() {
    const GoApp3=()=>{
        window.location.href='/estimation'
    }
    const addclothes=()=>{
        alert('옷추가')
    }

    return (
        <div id="App">
            <div id="header2">
                <div id="logo">
                    <img src={logo}/>
                </div>
                <div id="cody">
                    <div id="userdata">
                        유저이름
                    </div>
                    <div id="aichoose" onClick={GoApp3}>AI기반</div>
                    <div id="weatherchoose" onClick={GoApp3}>날씨기반</div>
                </div>
            </div>
            <div id="category2">
                카테고리
                <nav class="nav">
                    <ul class="gnb">
                        <li><a href="#">전체</a></li>
                        <li><a href="#">상의</a>
                            <ul class="lnb">
                                <li><a href="#">블라우스</a></li>
                                <li><a href="#">티</a></li>
                                <li><a href="#">셔츠</a></li>
                                <li><a href="#">니트</a></li>
                            </ul>
                        </li>
                        <li><a href="#">하의</a>
                            <ul class="lnb">
                                <li><a href="#">블라우스</a></li>
                                <li><a href="#">티</a></li>
                                <li><a href="#">셔츠</a></li>
                                <li><a href="#">니트</a></li>
                            </ul>
                        </li>
                        <li><a href="#">신발</a>
                            <ul class="lnb">
                                <li><a href="#">블라우스</a></li>
                                <li><a href="#">티</a></li>
                                <li><a href="#">셔츠</a></li>
                                <li><a href="#">니트</a></li>
                            </ul>
                        </li>
                        <li><a href="#">가방</a>
                            <ul class="lnb">
                                <li><a href="#">블라우스</a></li>
                                <li><a href="#">티</a></li>
                                <li><a href="#">셔츠</a></li>
                                <li><a href="#">니트</a></li>
                            </ul>
                        </li>
                        <li><a href="#">좋아요</a></li>
                        <li><a href="#">싫어요</a></li>
                    </ul>
                </nav> 
                <div id="colorcheck">
                    <div id='color'>
                        <label>             빨강</label>
                        <input type='checkbox' onClick={null}/>
                        <label>             노랑</label>
                        <input type='checkbox' onClick={null}/>
                        <label>             초록</label>
                        <input type='checkbox' onClick={null}/>
                        <label>             파랑</label>
                        <input type='checkbox' onClick={null}/>
                        <label>             보라</label>
                        <input type='checkbox' onClick={null}/>
                        <label>             하양</label>
                        <input type='checkbox' onClick={null}/>
                        <label>             검정</label>
                        <input type='checkbox' onClick={null}/>
                    </div>
                </div>
            </div>
            <div id="clolist">
                <div id="addclothes" onClick={addclothes}>
                    <input type='file' name='file' onChange={null}/>
                </div>
            </div>
        </div>
    );
}

export default App2;