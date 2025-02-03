import logo from "@/assets/img/image.png"

import { LoginForm } from "@/components/login/login-form"

export default function Login() {
  return (
    <div className="grid min-h-svh lg:grid-cols-2">
      <div className="flex flex-col gap-4 p-6 md:p-10">
        <div className="flex justify-center gap-2 md:justify-start">
          <a href="#" className="flex items-center gap-2 font-medium">
            <div className="flex h-19 w-19 items-center justify-center ">
              <img src={logo} className="size-16" alt="Logo" />
              <h3 className="text-[17px] font-['Dancing_Script'] uppercase tracking-wide font-bold">
                Administration
              </h3> 
            </div>
          </a>
        </div>
        <div className="flex flex-1 items-center justify-center">
          <div className="w-full max-w-xs">
            <LoginForm />
          </div>
        </div>
      </div>
      <div className="relative hidden bg-muted lg:block">
        <img
          src="https://maglor.fr/sites/default/files/zellige2.png"
          alt="Image"
          className="absolute inset-0 h-full w-full object-cover dark:brightness-[0.2] dark:grayscale"
        />
      </div>
    </div>
  )
}

