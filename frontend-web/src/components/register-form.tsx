import { cn } from "../lib/utils"
import { Button } from "./ui/button"
import {Card,CardContent,CardDescription,CardHeader,CardTitle,} from "./ui/card"
import {Field,FieldDescription,FieldGroup,FieldLabel} from "./ui/field"
import { Input } from "./ui/input"
import { Link } from "react-router-dom";

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
    <div className={ cn("flex flex-col gap-6", className)} {...props}>
     
      
    </div>
  )
}
