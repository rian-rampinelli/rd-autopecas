import ContainerPrincipal from "../../layout/container-principal/ContainerPrincipal"
import NavVertical from "../../components/nav-vertical/NavVertical"

import { RegisterForm } from "../../components/register-form"
import NavMain from "../../components/nav-main/NavMain"
function RegisterEmployee() {
    return (
        <ContainerPrincipal>
            <NavVertical></NavVertical>
            <main className="bg-[#151515] flex-1  ">
                <NavMain></NavMain>
                <RegisterForm className="w-[450px] " />
            </main>
        </ContainerPrincipal>
    )
}

export default RegisterEmployee