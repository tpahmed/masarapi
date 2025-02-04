"use client"

import { useEffect } from "react"
import { zodResolver } from "@hookform/resolvers/zod"
import { useForm } from "react-hook-form"
import * as z from "zod"

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
})

export function EditStudentDialog({ open, onOpenChange, student, onSave }) {
  const form = useForm({
    resolver: zodResolver(studentFormSchema),
    defaultValues: student,
  })

  useEffect(() => {
    if (student) {
      form.reset({
        name: student.name,
        email: student.email,
        level: student.level,
        age: student.age.toString(),
        yearOfInscription: student.yearOfInscription.toString(),
        class: student.class,
      })
    }
  }, [student, form])

  function onSubmit(values) {
    onSave({ ...values, id: student.id })
    onOpenChange(false)
  }

  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent className="sm:max-w-[550px] max-h-[80vh] overflow-y-auto border-gray-800 p-0">
        <DialogHeader className="relative px-6 py-4">
          <DialogTitle className="text-xl font-semibold">Edit Student</DialogTitle>
          <DialogDescription className="text-gray-400 text-sm">
            Edit student information. Click save when you&apos;re done.
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

