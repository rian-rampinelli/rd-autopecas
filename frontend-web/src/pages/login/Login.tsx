import ContainerCenter from "../../layout/container-center/ContainerCenter"
import "./Login.css"
import { LoginForm } from "../../components/login-form"
import Nav from "../../components/nav/Nav"




function Login() {
    return (
        <ContainerCenter>
            <Nav></Nav>
            <div className="flex">
                <LoginForm className="w-[450px]"/>
            </div>
        
        </ContainerCenter>
    )
}

export default Login