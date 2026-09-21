import ContainerLogin from "../../layout/ContainerLogin"
import "./Login.css"

import { LoginForm } from "../../components/login-form"
import Footer from "../../components/footer/Footer"
import Nav from "../../components/nav/Nav"



function Login() {
    return (
        <ContainerLogin>
            <Nav></Nav>
            <LoginForm className="w-[450px]"/>
            <Footer></Footer>
        </ContainerLogin>
    )
}

export default Login