import type { ReactNode } from "react"
import './styles/ContainerLogin.css'

function ContainerLogin({children}:{children: ReactNode}){
    return(
        <div className="container-login">
            {children}
        </div>
    )
}

export default ContainerLogin