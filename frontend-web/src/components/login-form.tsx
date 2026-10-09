import { cn } from "../lib/utils"
import { Button } from "../components/ui/button"
import { Card, CardContent, CardDescription, CardHeader, CardTitle, } from "../components/ui/card"
import { Field,  FieldGroup, FieldLabel } from "../components/ui/field"
import { Input } from "../components/ui/input"
import { useState } from "react"
import FazerLogin from "../api/Login"

export function LoginForm({ className, ...props }: React.ComponentProps<"div">) {
  const [email, setEmail] = useState("")
  const [senha, setSenha] = useState("")

  async function handleLogin(e: React.SubmitEvent<HTMLFormElement>) {
    e.preventDefault()
    const data = await FazerLogin(email,senha)
    console.log(data)

  }

  return (
    <div className={cn("flex flex-col gap-6", className)} {...props}>
      <Card>
        <CardHeader>
          <CardTitle>Entre com sua conta</CardTitle>
          <CardDescription>
            Digite seu email e senha abaixo para acessar
          </CardDescription>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleLogin}>
            <FieldGroup>
              <Field>
                <FieldLabel htmlFor="email">Email</FieldLabel>
                <Input
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  id="email"
                  type="email"
                  placeholder="seunome@example.com"
                  required
                />
              </Field>
              <Field>
                <div className="flex items-center">
                  <FieldLabel htmlFor="password">Password</FieldLabel>
                  <a
                    href="#"
                    className="ml-auto inline-block text-sm underline-offset-4 hover:underline"
                  >
                    Esqueceu a senha?
                  </a>
                </div>
                <Input onChange={(e) => setSenha(e.target.value)} value={senha} placeholder="************" id="password" type="password" required />
              </Field>
              <Field>
                <Button type="submit">Login</Button>
              </Field>
            </FieldGroup>
          </form>
        </CardContent>
      </Card>
    </div>
  )
}
