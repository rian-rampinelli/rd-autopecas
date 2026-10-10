import { cn } from "../lib/utils"
import { Input } from "./ui/input"


export function RegisterForm({className,...props}: React.ComponentProps<"div">) {
  /*
  const [email,SetEmail] = useState("")
  const [password,SetPassword] = useState("")
  const [confirmPassword,SetConfirmPassword] = useState("")
  const [cpf,SetCpf] = useState("")
  const [salary,SetSalary] = useState("")
  const [cargo,SetCargo] = useState("")
  */
 
  return (
    <div className="ml-18 mr-18" {...props}>
      <h1>Cadastrar funcionario</h1>
      <p>prenchaa os dados abaixo</p>
      <form className="mt-5" action="">
        <div className=" p-5 rounded-3xl border-1 border-white/10 bg-brand-bg-secundary pb-7">
          <h2 className="mb-5">Dados pessoais</h2>
          <div className="grid  grid-cols-3 gap-4 ">
            <div>
              <p>Nome completo *</p>
              <Input className="w-100 border-brand-bg-secundary" />
            </div>
             <div>
              <p>CPF *</p>
              <Input className="w-100" />
            </div>
             <div>
              <p>Data de nascimento</p>
         <Input className="w-100" />
            </div>
             <div>
              <p>Email</p>
              <Input className="w-100" />
            </div>
             <div>
              <p>Telefone</p>
             <Input className="w-100" />
            </div>
             <div>
              <p>Cidade</p>
              <Input className="w-100" />
            </div>
          </div>
        </div>
      </form>
     
      
    </div>
  )
}
