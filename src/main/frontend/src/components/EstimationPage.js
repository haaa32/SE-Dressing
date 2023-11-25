import '../css/EstimationPage.css';

function App3() {
    const GoApp2=()=>{
        window.location.href='/main'
    }

    const checkgood=()=>{
        alert("Good")
    }

    const checkbad=()=>{
        alert("Bad")
    }

    return (
        <div id="App">
            <div id="header3">
                    <div id="exit" onClick={GoApp2}>
                        나가기
                    </div>
                </div>
                <div id="codytest">
                    코디테스트
                    <div id="cpicture">
                        예시사진
                    </div>
                </div>
                <div id="GoodOrBad">
                    <div id="Good" onClick={checkgood}>
                        좋아요
                    </div>
                    <div id="Bad" onClick={checkbad}>
                        싫어요
                    </div>
                </div>
        </div>
    );
}

export default App3;