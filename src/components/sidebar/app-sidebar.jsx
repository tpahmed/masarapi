"use client"
import logo from "@/assets/img/image.png"

import { SidebarNav } from "./nav-projects"
import { NavUser } from "./nav-user"
import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarHeader,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
} from "@/components/ui/sidebar"

const data = {
  user: {
    name: "shadcn",
    email: "m@example.com",
    avatar: "https://github.com/shadcn.png",
  },

}

export function AppSidebar({ ...props }) {
  return (
    <Sidebar variant="inset" {...props}>
      <SidebarHeader>
        <SidebarMenu>
          <SidebarMenuItem>

            <SidebarMenuButton size="lg" asChild>
              <div className="flex items-center gap-3 px-2">
                <img src={logo} className="size-12" alt="Logo" />
                <div className="flex flex-col">
                  <span className="font-bold text-lg text-primary">School</span>
                  <span className="text-xs text-muted-foreground">Management System</span>
                </div>
              </div>
            </SidebarMenuButton>
          </SidebarMenuItem>
        </SidebarMenu>
      </SidebarHeader>
      <SidebarContent>
        <SidebarNav  />
      </SidebarContent>
      <SidebarFooter>
        <NavUser user={data.user} />
      </SidebarFooter>
    </Sidebar>
  )
}

