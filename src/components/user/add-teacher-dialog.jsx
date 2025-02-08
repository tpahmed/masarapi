"use client"

import { useState, useEffect } from "react"
import { zodResolver } from "@hookform/resolvers/zod"
import { useForm } from "react-hook-form"
import * as z from "zod"
import { Eye, EyeOff } from "lucide-react"
import axios from "axios"

import { Button } from "@/components/ui/button"
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogDescription } from "@/components/ui/dialog"
import { Form, FormControl, FormField, FormItem, FormLabel } from "@/components/ui/form"
import { Input } from "@/components/ui/input"

const teacherFormSchema = z.object({
  name: z.string().min(2, "Name must be at least 2 characters"),
  email: z.string().email("Invalid email address"),
  subject: z.string().min(2, "Subject must be at least 2 characters"),
  experienceYears: z.string().refine((val) => !isNaN(Number(val)) && Number(val) >= 0, {
    message: "Experience years must be a positive number",
  }),
  qualification: z.string().min(2, "Qualification must be at least 2 characters"),
  password: z.string().min(6, "Password must be at least 6 characters"),
  confirmPassword: z.string(),
}).refine((data) => data.password === data.confirmPassword, {
  message: "Passwords don't match",
  path: ["confirmPassword"],
})

export function AddTeacherDialog({ open, onOpenChange, onSuccess }) {
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState("")
  const [showPassword, setShowPassword] = useState(false)
  const [showConfirmPassword, setShowConfirmPassword] = useState(false)
  const [isPasswordFilled, setIsPasswordFilled] = useState(false)

  const form = useForm({
    resolver: zodResolver(teacherFormSchema),
    defaultValues: {
      name: "",
      email: "",
      subject: "",
      experienceYears: "",
      qualification: "",
      password: "",
      confirmPassword: "",
    },
  })

  useEffect(() => {
    const password = form.watch("password")
    setIsPasswordFilled(password.length >= 1)
  }, [form])

  async function onSubmit(values) {
    try {
      setLoading(true)
      setError("")

      // First create the user account
      await axios.post(`${import.meta.env.VITE_API_URL}/api/auth/signup`, {
        nom: values.name,
        email: values.email,
        password: values.password,
        role: "TEACHER"
      })

      // Split the full name into first and last name
      const [prenom, nom] = values.name.split(" ")

      // Then create the teacher profile
      await axios.post(`${import.meta.env.VITE_API_URL}/api/enseignants`, {
        nom: nom || "",
        prenom: prenom || "",
        matiere: values.subject,
        qualification: values.qualification,
        anneesExperience: parseInt(values.experienceYears)
      })

      onOpenChange(false)
      if (onSuccess) onSuccess()
    } catch (error) {
      console.error("Error adding teacher:", error)
      setError(error.response?.data?.message || "Failed to add teacher. Please try again.")
    } finally {
      setLoading(false)
    }
  }

  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent className="sm:max-w-[550px] max-h-[80vh] overflow-y-auto border-gray-800 p-0">
        <DialogHeader className="relative px-6 py-4">
          <DialogTitle className="text-xl font-semibold">Add New Teacher</DialogTitle>
          <DialogDescription className="text-gray-400 text-sm">
            Add a new teacher to the system. Click save when you&apos;re done.
          </DialogDescription>
          {error && <p className="text-sm text-red-500 mt-2">{error}</p>}
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
                        placeholder="Dr. John Smith"
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
                        placeholder="john.smith@school.com"
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
                name="subject"
                render={({ field }) => (
                  <FormItem className="grid grid-cols-[120px_1fr] items-center gap-2">
                    <FormLabel className="text-sm text-right">Subject</FormLabel>
                    <FormControl>
                      <Input
                        placeholder="Mathematics"
                        {...field}
                        className="border border-gray-200 dark:border-gray-800 h-9 text-sm placeholder:text-gray-500"
                      />
                    </FormControl>
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="experienceYears"
                render={({ field }) => (
                  <FormItem className="grid grid-cols-[120px_1fr] items-center gap-2">
                    <FormLabel className="text-sm text-right">Experience (Years)</FormLabel>
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
                name="qualification"
                render={({ field }) => (
                  <FormItem className="grid grid-cols-[120px_1fr] items-center gap-2">
                    <FormLabel className="text-sm text-right">Qualification</FormLabel>
                    <FormControl>
                      <Input
                        placeholder="Ph.D. Mathematics"
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
                disabled={loading}
              >
                {loading ? "Saving..." : "Save changes"}
              </Button>
            </div>
          </form>
        </Form>
      </DialogContent>
    </Dialog>
  )
}

