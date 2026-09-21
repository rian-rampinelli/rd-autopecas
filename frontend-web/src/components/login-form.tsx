import { cn } from "../lib/utils"

import { Button } from "../components/ui/button"
import {Card,CardContent,CardDescription,CardHeader,CardTitle,} from "../components/ui/card"
import {Field,FieldDescription,FieldGroup,FieldLabel} from "../components/ui/field"
import { Input } from "../components/ui/input"

export function LoginForm({className,...props}: React.ComponentProps<"div">) {
  return (
    <div className={ cn("flex flex-col gap-6", className)} {...props}>
      <Card>
        <CardHeader>
          <CardTitle>Entre com sua conta</CardTitle>
          <CardDescription>
            Digite seu email e senha abaixo para acessar
          </CardDescription>
        </CardHeader>
        <CardContent>
          <form>
            <FieldGroup>
              <Field>
                <FieldLabel htmlFor="email">Email</FieldLabel>
                <Input
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
                <Input placeholder="************" id="password" type="password" required />
              </Field>
              <Field>
                <Button type="submit">Login</Button>
                <FieldDescription className="text-center">
                  Não tem conta ainda? <a className=":hover text-blue-400" href="#">Cadastre-se</a>
                </FieldDescription>
              </Field>
            </FieldGroup>
          </form>
        </CardContent>
      </Card>
    </div>
  )
}
