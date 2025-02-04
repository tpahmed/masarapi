import { useState, useEffect } from "react"
import { zodResolver } from "@hookform/resolvers/zod"
import { useForm } from "react-hook-form"
import * as z from "zod"
import { Eye, EyeOff } from "lucide-react"

import { Button } from "@/components/ui/button"
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogDescription } from "@/components/ui/dialog"
import { Form, FormControl, FormField, FormItem, FormLabel } from "@/components/ui/form"
import { Input } from "@/components/ui/input"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"

const studentFormSchema = z.object({
  name: z.string().min(2, "Name must be at least 2 characters"),
  email: z.string().email("Invalid email address"),
  level: z.enum(["Beginner", "Intermediate", "Advanced"]),
  age: z.string().refine((val) => !isNaN(Number(val)) && Number(val) > 0, {
    message: "Age must be a positive number",
  }),
  yearOfInscription: z.string().refine((val) => !isNaN(Number(val)) && Number(val) > 2000, {
    message: "Please enter a valid year",
  }),
  class: z.string().min(2, "Class must be at least 2 characters"),
  password: z.string().min(8, "Password must be at least 8 characters"),
  confirmPassword: z.string(),
}).refine((data) => data.password === data.confirmPassword, {
  message: "Passwords don't match",
  path: ["confirmPassword"],
})

export function AddStudentDialog(props) {
  const { open, onOpenChange } = props
  const [showPassword, setShowPassword] = useState(false)
  const [showConfirmPassword, setShowConfirmPassword] = useState(false)
  const [isPasswordFilled, setIsPasswordFilled] = useState(false)

  const form = useForm({
    resolver: zodResolver(studentFormSchema),
    defaultValues: {
      name: "",
      email: "",
      level: "",
      age: "",
      yearOfInscription: new Date().getFullYear().toString(),
      class: "",
      password: "",
      confirmPassword: "",
    },
  })

  useEffect(() => {
    const password = form.watch("password")
    setIsPasswordFilled(password.length >= 1)
  }, [form])

  function onSubmit(values) {
    console.log(values)
    onOpenChange(false)
  }

  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent className="sm:max-w-[550px] max-h-[80vh] bg-gray-200 overflow-y-auto border-gray-800 p-0">
        <DialogHeader className="relative px-6 py-4">
          <DialogTitle className="text-xl font-semibold">Add New Student</DialogTitle>
          <DialogDescription className="text-gray-400 text-sm">
            Add a new student to the system. Click save when you&apos;re done.
          </DialogDescription>
        </DialogHeader>
        <Form {...form}>
          <form onSubmit={form.handleSubmit(onSubmit)} className="px-6 pb-6">
            <div className="space-y-3 py-2">
              <FormField
                control={form.control}
                name="name"
                render={({ field }) => (
                  <FormItem className="grid grid-cols-[120px_1fr] items-center gap-2">
                    <FormLabel className="text-sm text-right">Full Name</FormLabel>
                    <FormControl>
                      <Input
                        placeholder="John Doe"
                        {...field}
                        className="border h-9 border-gray-200 dark:border-gray-800 text-sm placeholder:text-gray-500"
                      />
                    </FormControl>
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="email"
                render={({ field }) => (
                  <FormItem className="grid grid-cols-[120px_1fr] items-center gap-2">
                    <FormLabel className="text-sm text-right">Email</FormLabel>
                    <FormControl>
                      <Input
                        placeholder="john.doe@school.com"
                        type="email"
                        {...field}
                        className="border border-gray-200 dark:border-gray-800 h-9 text-sm placeholder:text-gray-500"
                      />
                    </FormControl>
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="level"
                render={({ field }) => (
                  <FormItem className="grid grid-cols-[120px_1fr] items-center gap-2">
                    <FormLabel className="text-sm text-right">Level</FormLabel>
                    <Select onValueChange={field.onChange} defaultValue={field.value}>
                      <FormControl>
                        <SelectTrigger className="border border-gray-200 dark:border-gray-800 h-9 text-sm">
                          <SelectValue placeholder="Select a level" />
                        </SelectTrigger>
                      </FormControl>
                      <SelectContent>
                        <SelectItem value="Beginner">Beginner</SelectItem>
                        <SelectItem value="Intermediate">Intermediate</SelectItem>
                        <SelectItem value="Advanced">Advanced</SelectItem>
                      </SelectContent>
                    </Select>
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="age"
                render={({ field }) => (
                  <FormItem className="grid grid-cols-[120px_1fr] items-center gap-2">
                    <FormLabel className="text-sm text-right">Age</FormLabel>
                    <FormControl>
                      <Input
                        type="number"
                        {...field}
                        className="border border-gray-200 dark:border-gray-800 h-9 text-sm placeholder:text-gray-500"
                      />
                    </FormControl>
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="yearOfInscription"
                render={({ field }) => (
                  <FormItem className="grid grid-cols-[120px_1fr] items-center gap-2">
                    <FormLabel className="text-sm text-right">Year of Inscription</FormLabel>
                    <FormControl>
                      <Input
                        type="number"
                        {...field}
                        className="border border-gray-200 dark:border-gray-800 h-9 text-sm placeholder:text-gray-500"
                      />
                    </FormControl>
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="class"
                render={({ field }) => (
                  <FormItem className="grid grid-cols-[120px_1fr] items-center gap-2">
                    <FormLabel className="text-sm text-right">Class</FormLabel>
                    <FormControl>
                      <Input
                        placeholder="10A"
                        {...field}
                        className="border border-gray-200 dark:border-gray-800 h-9 text-sm placeholder:text-gray-500"
                      />
                    </FormControl>
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="password"
                render={({ field }) => (
                  <FormItem className="grid grid-cols-[120px_1fr] items-center gap-2">
                    <FormLabel className="text-sm text-right">Password</FormLabel>
                    <FormControl>
                      <div className="relative">
                        <Input
                          type={showPassword ? "text" : "password"}
                          placeholder="Enter password"
                          {...field}
                          className="border border-gray-200 dark:border-gray-800 h-9 text-sm placeholder:text-gray-500 pr-10"
                        />
                        <Button
                          type="button"
                          variant="ghost"
                          size="icon"
                          className="absolute right-0 top-0 h-full px-2 py-1 hover:bg-transparent text-gray-400"
                          onClick={() => setShowPassword(!showPassword)}
                        >
                          {showPassword ? <EyeOff className="h-3 w-3" /> : <Eye className="h-3 w-3" />}
                        </Button>
                      </div>
                    </FormControl>
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="confirmPassword"
                render={({ field }) => (
                  <FormItem className="grid grid-cols-[120px_1fr] items-center gap-2">
                    <FormLabel className="text-sm text-right">Confirm Password</FormLabel>
                    <FormControl>
                      <div className="relative">
                        <Input
                          type={showConfirmPassword ? "text" : "password"}
                          placeholder="Confirm password"
                          {...field}
                          disabled={!isPasswordFilled}
                          className={`border border-gray-200 dark:border-gray-800 h-9 text-sm placeholder:text-gray-500 pr-10 ${
                            !isPasswordFilled ? "opacity-50 cursor-not-allowed" : ""
                          }`}
                        />
                        <Button
                          type="button"
                          variant="ghost"
                          size="icon"
                          className="absolute right-0 top-0 h-full px-2 py-1 hover:bg-transparent text-gray-400"
                          onClick={() => setShowConfirmPassword(!showConfirmPassword)}
                          disabled={!isPasswordFilled}
                        >
                          {showConfirmPassword ? <EyeOff className="h-3 w-3" /> : <Eye className="h-3 w-3" />}
                        </Button>
                      </div>
                    </FormControl>
                  </FormItem>
                )}
              />
            </div>
            <div className="mt-4 flex justify-end py-4 bg-opacity-20">
              <Button
                type="submit"
                className="h-9 px-4 text-sm dark:bg-white dark:text-black hover:bg-gray-800 bg-black text-white dark:hover:bg-gray-200"
              >
                Save changes
              </Button>
            </div>
          </form>
        </Form>
      </DialogContent>
    </Dialog>
  )
}

