import ContainerCenter from "../../layout/container-center/ContainerCenter"
import Nav from "../../components/nav/Nav"
import { RegisterForm } from "../../components/register-form"



function Register() {
    return (
        <ContainerCenter>
            <Nav></Nav>
            <RegisterForm className="w-[450px]"/>
        </ContainerCenter>
    )
}

export default Register