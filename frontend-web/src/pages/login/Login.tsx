import { useState } from "react"
import ContainerLogin from "../../layout/ContainerLogin"
import "./Login.css"
import Input from "../../components/Input"

function Login() {
    
    const [email,setEmail] = useState("")
    const [senha,setSenha] = useState("")

    return (
        <ContainerLogin>
            <div className="box-login">
                <div>
                    <li></li>
                    <li></li>
                    <li></li>
                    <li></li>
                </div>
                <h1 className="text-white">Autopeças RD</h1>
                <form className="flex flex-col gap-4 w-112.5 ">
                    <Input type="email" value={email} onChange={(e)=>setEmail(e.target.value)} placeholder="Digite seu e-mail"></Input>
                    <Input type="password" value={senha} onChange={(e)=>setSenha(e.target.value)} placeholder="Digite sua senha" />
                    <button className="bg-white text-black  py-3 px-5 rounded-2xl ">Fazer Login</button>
                </form>
            </div>
        </ContainerLogin>
    )
}

export default Login