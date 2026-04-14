

export default function Header() {
    return (

        <>
            <div className="navigation">
                <ul>
                <li>Feed</li>
                <li>Profile</li>

                </ul>

                <div className="hamburger">
                    <div className="burger burger1" />
                    <div className="burger burger2" />
                    <div className="burger burger3" />
                </div>

            </div>


            <style jsx> {`
                .navigation {
                    width: 100%;
                    height: 50px;
                }

                .navigation ul {
                    display: flex;
                    flex-wrap: wrap;
                    float: right;
                    margin: 20 0px;
                    padding: 0 25px;
                }

                .navigation {
                    list-style-type: name;
                    padding-right: 10px;
                }
                
                .hamburger {
                    width: 2rem;
                    height: 2rem;
                    display: flex;
                    justifycontent: space-around;
                    flex-flow: column nowrap;
                    z-index: 10;
                }

                .burger {
                    width: 2rem;
                    height: 0.25rem;
                    border-radius: 10px;
                    background-color: black;
                    transformation-origin: 1px;
                    transition: all 0,3s linear;
                }
            `}</style>
        </>

    )
}