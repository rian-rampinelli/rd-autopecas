import ContainerPrincipal from "../../layout/container-principal/ContainerPrincipal"
import NavVertical from "../../components/nav-vertical/NavVertical"
import { RegisterForm } from "../../components/register-form"
import NavMain from "../../components/nav-main/NavMain"

function RegisterEmployee() {
  return (
    <ContainerPrincipal>
      <NavVertical />
      <main className="min-w-0 flex-1 bg-[#151515]">
        <NavMain />
        <RegisterForm />
      </main>
    </ContainerPrincipal>
  )
}

export default RegisterEmployee
