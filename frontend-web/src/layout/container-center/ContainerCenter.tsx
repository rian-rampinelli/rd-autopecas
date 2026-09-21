import type { ReactNode } from "react"
import './ContainerCenter.css'

function ContainerCenter({children}:{children: ReactNode}){
    return(
        <div className="container-login">
            {children}
        </div>
    )
}

export default ContainerCenter